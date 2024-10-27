from flask import Flask, request, send_file
import pandas as pd
import uuid
import os
import random
from io import BytesIO
import numpy as np
import functions as fnc

app = Flask(__name__)

# Маршрут API для предсказания выхода на пенсию
@app.route('/api/predict', methods=['POST'])
def predict_retirement():

    # Получаем файлы из запроса
    personal_data_file = request.files['personalData']  # Файл с персональными данными
    txn_data_file = request.files['txnData']            # Файл с данными транзакций

    # Читаем содержимое файлов как DataFrame
    info_df = pd.read_csv(personal_data_file)
    transaction_df = pd.read_csv(txn_data_file)

    # Добавляем год выхода на пенсию и вычисляем остаток средств
    result_df = fnc.add_retirement_year(info_df, transaction_df)
    result_df = fnc.compute_money_left(result_df, transaction_df)

    # Устанавливаем конечный возраст выхода на пенсию в зависимости от пола
    result_df["retirement_end_year"] = result_df.gndr.apply(lambda x: 82 if x=="ж" else 87)

    # Рассчитываем годовой пенсионный платеж
    result_df["yearly_pension_payment"] = result_df["money_left"] / np.where(
        (result_df["retirement_end_year"] - result_df["prsnt_age"]) == 0,
        1,
        (result_df["retirement_end_year"] - result_df["prsnt_age"])
    )

    # Выполняем предсказание и объединяем с основными данными
    preds = fnc.prod_predict(result_df)
    if 'erly_pnsn_flg' in result_df.columns:
        result_df.drop("erly_pnsn_flg", axis=1, inplace=True)
    result_df = pd.merge(result_df, preds, on='clnt_id', how='inner')
    final_df = result_df[["accnt_id", "erly_pnsn_flg", "yearly_pension_payment"]]

    # Записываем результат в CSV
    result_csv = final_df.to_csv(index=False)
    result_csv_io = BytesIO(result_csv.encode())
    result_csv_io.seek(0)
    
    # Отправляем CSV файл обратно
    return send_file(result_csv_io, mimetype='text/csv', as_attachment=True, download_name='PersonRetirementDataToAnalyze.csv')

# Запуск сервера
if __name__ == '__main__':
    app.run(host='localhost', port=5000)
