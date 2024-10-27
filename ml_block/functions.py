import pandas as pd
import numpy as np

# Функция для вычисления флага досрочного выхода на пенсию с учетом гендера и региона
def prod_predict(df):
    # Конвертируем пол в числовой формат: 1 - мужской, 0 - женский
    df['gndr'] = df['gndr'].apply(lambda x: 1 if x == 'м' else 0)
    
    # Отмечаем северные регионы, где действуют особые пенсионные условия
    df['north'] = df['rgn'].apply(lambda x: 1 if x in [
        'САХА /ЯКУТИЯ/ РЕСП', 'ЧУКОТСКИЙ АО', 'ЯМАЛО-НЕНЕЦКИЙ АО', 
        'НЕНЕЦКИЙ АО', 'МУРМАНСКАЯ ОБЛ', 'КАМЧАТСКИЙ КРАЙ', 'ЧУКОТСКИЙ АО'] else 0)
    
    # Рассчитываем флаг досрочной пенсии на основе гендера, возраста выхода на пенсию и региональных условий
    df['erly_pnsn_flg'] = np.round(
        -0.95338 * df['gndr'] + 
        1.87692 * ((df['pnsn_age'] - 55) / 10) + 
        0.00818 * df['north'] - 0.01382
    ).astype(int)
    
    return df[['clnt_id', 'erly_pnsn_flg']]

# Упрощенная функция для вычисления флага досрочной пенсии
def predict(df):
    df['gndr'] = df['gndr'].apply(lambda x: 1 if x == 'м' else 0)
    df['erly_pnsn_flg'] = np.round(
        -0.95473 * df['gndr'] + 
        1.87896 * ((df['pnsn_age'] - 55) / 10) - 0.01358
    ).astype(int)
    
    return df[['clnt_id', 'erly_pnsn_flg']]

# Функция для заполнения отсутствующих дат выхода на пенсию
def fill_missing_retirement_date(row):
    # Если дата выхода на пенсию отсутствует, применяем логику для ее расчета
    if pd.isna(row['retirement_year']):
        # Устанавливаем возраст выхода на пенсию в зависимости от пола
        if row['gndr'] == 'ж':
            return min(row["brth_yr"] + 55, 2024)
        else:
            return min(row["brth_yr"] + 60, 2024)
    else:
        # Оставляем исходное значение, если оно не пустое
        return max(row['retirement_year'], 2024)

# Функция для добавления даты выхода на пенсию на основе транзакционных данных
def add_retirement_year(info_df, transaction_df):
    transaction_df['oprtn_date'] = pd.to_datetime(transaction_df['oprtn_date'])
    
    # Список комментариев, указывающих на начало выплат
    comments = [
        "Решение о единовременной выплате (ОПС)",
        "Начисление пенсии (ОПС)",
        "Назначение пенсии (ОПС)"
    ]
    
    # Фильтруем транзакции с указанными комментариями
    filtered_df = transaction_df[transaction_df['cmmnt'].isin(comments)]
    
    # Группируем по 'accnt_id' и находим минимальную дату
    result_df = filtered_df.groupby('accnt_id')['oprtn_date'].min().reset_index()
    result_df.rename(columns={'oprtn_date': 'retirement_year'}, inplace=True)
    
    # Объединяем с данными о клиентах
    merged_df = info_df.merge(result_df, on='accnt_id', how='left')
    merged_df['retirement_year'] = merged_df['retirement_year'].dt.year
    
    # Заполняем отсутствующие даты выхода на пенсию
    merged_df['retirement_year'] = merged_df.apply(fill_missing_retirement_date, axis=1)
    
    return merged_df

# Функция для расчета остатка средств у клиента
def compute_money_left(info_df, transaction_df):
    # Группируем по 'accnt_id' и 'mvmnt_type', суммируя значения в столбце 'sum'
    grouped = transaction_df.groupby(['accnt_id', 'mvmnt_type'])['sum'].sum().unstack(fill_value=0)
    
    # Убеждаемся, что для всех accnt_id присутствуют оба типа mvmnt_type (0 и 1)
    for mvmnt in [0, 1]:
        if mvmnt not in grouped.columns:
            grouped[mvmnt] = 0
    
    # Вычисляем остаток средств, вычитая сумму с типом 1 из типа 0
    grouped['money_left'] = (grouped[0] - grouped[1]).clip(lower=0)
    
    # Сбрасываем индекс для объединения с info_df
    grouped = grouped.reset_index()
    
    # Объединяем 'money_left' с данными о клиентах
    result_df = info_df.merge(grouped[['accnt_id', 'money_left']], on='accnt_id', how='left')
    
    # Заполняем пропущенные значения в 'money_left' нулями, если для accnt_id нет транзакций
    result_df['money_left'] = result_df['money_left'].fillna(0)
    
    return result_df
