import { useState } from 'react';
import { Table, Typography } from 'antd';
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
