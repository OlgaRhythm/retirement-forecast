import { useState } from 'react';
import { Table, Typography, Button } from 'antd';
import FileUpload from '../FileManager/FileUpload';

const { Title } = Typography;

/**
 * Компонент ResultView, который отображает результаты загрузки файлов и предсказание
 * досрочного выхода на пенсию в табличной форме.
 * 
 * @returns {JSX.Element} Компонент для отображения результатов
 */
const ResultView = () => {
    const [resultData, setResultData] = useState(null); // Состояние для данных результата загрузки файлов

    // Определение столбцов таблицы для отображения данных прогноза
    const columns = [
        {
            title: 'Account ID',
            dataIndex: 'accountId',
            key: 'accountId',
        },
        {
            title: 'Ранний выход на пенсию',
            dataIndex: 'isRetirementEarly',
            key: 'isRetirementEarly',
            render: (isEarly) => (isEarly ? 'Да' : 'Нет'), // Форматирование значения
        },
    ];

    /**
     * Функция для скачивания данных таблицы в формате CSV.
     */
    const downloadTableAsCsv = () => {
        if (!resultData?.predictedRetirementData) return;
    
        // Заголовки CSV
        const headers = ['accnt_id', 'erly_pnsn_flg'];
        const csvRows = [headers.join(',')];
    
        // Данные CSV с 1 и 0 вместо "Да" и "Нет"
        resultData.predictedRetirementData.forEach((row) => {
            csvRows.push([row.accountId, row.isRetirementEarly ? '1' : '0'].join(','));
        });
    
        // Конвертируем в формат Blob с кодировкой UTF-8 и создаем ссылку для скачивания
        const csvData = new Blob([`\uFEFF${csvRows.join('\n')}`], { type: 'text/csv;charset=utf-8;' });
        const csvUrl = URL.createObjectURL(csvData);
        const link = document.createElement('a');
        link.href = csvUrl;
        link.download = 'predicted_retirement_data.csv';
        link.click();
    };

    return (
        <div>
            {/* Компонент загрузки файлов с передачей функции для установки результата */}
            <FileUpload setResponce={setResultData} />

            {resultData ? (
                <>
                    <Title level={3}>
                        Затраты на выплату досрочной пенсии в период с 2022 до 2024: {resultData.earlyRetirementCosts}
                    </Title>
                    <Title level={3}>
                        Затраты на выплату всего объёма пенсии в период с 2022 до 2024: {resultData.overallRetirementCosts}
                    </Title>
                    <br />
                    <Title level={3}>Результат прогноза досрочного выхода на пенсию:</Title>

                    {/* Кнопка для скачивания таблицы как CSV */}
                    <Button
                        type="primary"
                        style={{ marginBottom: '16px' }}
                        onClick={downloadTableAsCsv}
                    >
                        Скачать таблицу в формате CSV
                    </Button>

                    {/* Таблица для отображения предсказанных данных о выходе на пенсию */}
                    <Table
                        dataSource={resultData.predictedRetirementData}
                        columns={columns}
                        rowKey="accountId"
                        pagination={false}
                        locale={{ emptyText: 'Нет данных для отображения' }} // Сообщение при отсутствии данных
                    />
                </>
            ) : (
                <Title level={4}>Пожалуйста, загрузите файлы для отображения результатов</Title>
            )}
        </div>
    );
};

export default ResultView;
