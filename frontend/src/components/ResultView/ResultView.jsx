import { useState } from 'react';
import { Table } from 'antd';
import FileUpload from '../FileManager/FileUpload'; // Импортируем FileUpload

const ResultView = () => {
    const [resultData, setResultData] = useState(null); // Состояние для данных результата загрузки файлов

    // Определение столбцов таблицы
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
        },
    ];

    return (
        <div>
            {/* Передаем функцию для обновления результата в FileUpload */}
            <FileUpload setResponce={setResultData} />

            {/* Отображаем данные результата загрузки файлов */}
            {resultData && (
                <>
                    <h3>Затраты на выплату досрочной пенсии в период с 2022 до 2024: {resultData.earlyRetirementCosts}</h3>
                    <h3>Затраты на выплату всего объёма пенсии в период с 2022 до 2024: {resultData.overallRetirementCosts}</h3>
                    <br />
                    <h3>Результат прогноза досрочного выхода на пенсию:</h3>

                    <Table
                       dataSource={resultData.predictedRetirementData} // Используем predictedRetirementData для таблицы
                        columns={columns}
                        rowKey="accountId" // Используем accountId как уникальный ключ
                        pagination={false}
                    />
                </>
            )}
        </div>
    );
};

export default ResultView;