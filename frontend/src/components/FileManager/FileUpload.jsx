import React, { useState } from 'react';
import { Button } from "antd";
import cl from './FileUpload.module.css';
import { apiUploadFiles } from '../../api/FileService';

/**
 * Компонент для загрузки и отправки двух CSV файлов на сервер.
 * 
 * @component
 * @param {function} setResponce - Функция для установки полученного ответа с сервера.
 * @returns {JSX.Element} - Форма загрузки и отправки файлов.
 */
const FileUpload = ({ setResponce }) => {
    const [loading, setLoading] = useState(false); // Индикатор состояния загрузки
    const [files, setFiles] = useState({ personalData: null, txnData: null }); // Состояние для хранения файлов
    const [uploadSuccess, setUploadSuccess] = useState(false); // Статус успешной загрузки
    const [csvData, setCsvData] = useState(null); // Состояние для данных CSV

    /**
     * Обработчик выбора файлов.
     * Проверяет, что выбранный файл имеет формат .csv.
     * 
     * @param {Event} event - Событие выбора файла.
     * @param {string} type - Тип файла ('personalData' или 'txnData').
     */
    const handleFileChange = (event, type) => {
        const selectedFile = event.target.files[0];
        if (selectedFile && selectedFile.name.endsWith('.csv')) {
            setFiles((prevFiles) => ({ ...prevFiles, [type]: selectedFile }));
        } else {
            alert(`Пожалуйста, выберите файл формата .csv для ${type === 'personalData' ? 'личных данных' : 'транзакций'}`);
        }
    };

    /**
     * Отправляет файлы на сервер.
     * Показывает индикатор загрузки, отправляет файлы с помощью `apiUploadFiles`,
     * затем обновляет состояние в зависимости от успеха или ошибки.
     */
    const uploadFiles = async () => {
        const { personalData, txnData } = files;
        if (!personalData || !txnData) {
            alert("Пожалуйста, выберите оба файла перед загрузкой.");
            return;
        }

        setLoading(true); // Показ индикатора загрузки
        setUploadSuccess(false); // Сброс статуса успешной загрузки

        try {
            const result = await apiUploadFiles(personalData, txnData); // Вызов API для загрузки файлов
            setResponce(result); // Установка ответа сервера
            setCsvData(result); // Установка данных CSV для скачивания
            setUploadSuccess(true); // Установка статуса успешной загрузки
            console.log('Файлы успешно загружены:', result);
        } catch (error) {
            console.error('Ошибка при загрузке файлов:', error);
            alert('Произошла ошибка при загрузке файлов.');
        } finally {
            setLoading(false); // Скрытие индикатора загрузки
        }
    };

    /**
     * Генерирует и скачивает CSV файл.
     */
    const downloadCsv = () => {
        const blob = new Blob([csvData], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'PersonRetirementDataToAnalyze.csv';
        link.click();
        window.URL.revokeObjectURL(url);
    };

    return (
        <form className={cl.form} onSubmit={(e) => { e.preventDefault(); uploadFiles(); }}>
            <div className={cl.container}>
                {['personalData', 'txnData'].map((type) => (
                    <div key={type} className={cl.buttonContainer}>
                        {/* Инпут для выбора файлов */}
                        <input
                            type="file"
                            accept=".csv" // Разрешаем только файлы формата CSV
                            onChange={(e) => handleFileChange(e, type)}
                            style={{ display: 'none' }}
                            id={`${type}Input`}
                        />
                        <Button
                            className={cl.btn}
                            onClick={() => document.getElementById(`${type}Input`).click()}
                            disabled={loading} // Отключаем кнопку при загрузке
                        >
                            {files[type] ? `Файл выбран: ${files[type].name}` : `Загрузите CSV файл с ${type === 'personalData' ? 'персональными данными' : 'данными о транзакциях'}`}
                        </Button>
                    </div>
                ))}
                <div className={cl.buttonContainer}>
                    {/* Кнопка для отправки файлов на сервер */}
                    <Button
                        className={cl.btn}
                        type="primary"
                        onClick={uploadFiles}
                        disabled={loading || !files.personalData || !files.txnData || uploadSuccess} // Условное отключение кнопки
                    >
                        {uploadSuccess ? 'Данные успешно отправлены' : 'Отправить данные на сервер'}
                    </Button>
                </div>
            </div>
        </form>
    );
};

export default FileUpload;
