import React, { useState } from 'react';
import { Button } from "antd";
import cl from './FileUpload.module.css';
import { apiUploadFiles } from '../../api/FileService';

/**
 * Компонент для загрузки и отправки двух CSV файлов на сервер
 * @param {*} param0
 * @returns
 */
const FileUpload = ({ setResponce }) => {
    const [loading, setLoading] = useState(false); // Индикатор загрузки
    const [personalDataFile, setPersonalDataFile] = useState(null); // Хранит файл personalData
    const [txnDataFile, setTxnDataFile] = useState(null); // Хранит файл txnData
    const [uploadSuccess, setUploadSuccess] = useState(false); // Хранит статус отправки

    // Функция для выбора файла с персональными данными в формате CSV
    const handlePersonalDataChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile && selectedFile.name.endsWith('.csv')) {
            setPersonalDataFile(selectedFile);
        } else {
            alert("Пожалуйста, выберите файл формата .csv для личных данных");
        }
    };

    // Функция для выбора файла с транзакциями в формате CSV
    const handleTxnDataChange = (event) => {
        const selectedFile = event.target.files[0];
        if (selectedFile && selectedFile.name.endsWith('.csv')) {
            setTxnDataFile(selectedFile);
        } else {
            alert("Пожалуйста, выберите файл формата .csv для транзакционных данных");
        }
    };

    // Функция для отправки данных на сервер
    const uploadFiles = async () => {
        if (!personalDataFile || !txnDataFile) {
            alert("Пожалуйста, выберите оба файла перед загрузкой.");
            return;
        }
        setLoading(true); // Показываем индикатор загрузки
        setUploadSuccess(false); // Сбрасываем статус перед новой попыткой отправки
        try {
            const result = await apiUploadFiles(personalDataFile, txnDataFile); // Вызов функции из API
            setResponce(result); // Устанавливаем результат (если нужно)
            setUploadSuccess(true); // Устанавливаем успешный статус
            console.log('Файлы успешно загружены:', result);
        } catch (error) {
            console.error('Ошибка при загрузке файлов:', error);
            alert('Произошла ошибка при загрузке файлов.');
        } finally {
            setLoading(false); // Скрываем индикатор загрузки
        }
    };

    return (
        <form className={cl.form} onSubmit={(e) => { e.preventDefault(); uploadFiles(); }}>
            <div className={cl.container}>
                <div className={cl.buttonContainer}>
                    {/* Input для выбора файла с персональными данными */}
                    <input
                        type="file"
                        accept=".csv" // Разрешаем только CSV
                        onChange={handlePersonalDataChange}
                        style={{ display: 'none' }}
                        id="personalDataInput"
                    />
                    <Button
                        className={cl.btn}
                        onClick={() => document.getElementById('personalDataInput').click()}
                        disabled={loading} // Блокируем кнопку во время загрузки
                    >
                        {personalDataFile ? `Файл выбран: ${personalDataFile.name}` : 'Загрузите CSV файл с персональными данными'}
                    </Button>
                </div>
                <div className={cl.buttonContainer}>
                    {/* Input для выбора файла с транзакциями */}
                    <input
                        type="file"
                        accept=".csv" // Разрешаем только CSV
                        onChange={handleTxnDataChange}
                        style={{ display: 'none' }}
                        id="txnDataInput"
                    />
                    <Button
                        className={cl.btn}
                        onClick={() => document.getElementById('txnDataInput').click()}
                        disabled={loading} // Блокируем кнопку во время загрузки
                    >
                        {txnDataFile ? `Файл выбран: ${txnDataFile.name}` : 'Загрузите CSV файл с данными о транзакциях'}
                    </Button>
                </div>
                {/* Контейнер для кнопок отправки */}
                <div className={cl.buttonContainer}>
                    {/* Кнопка для отправки файлов на сервер */}
                    <Button
                        className={cl.btn}
                        type="primary"
                        onClick={uploadFiles}
                        disabled={loading || !personalDataFile || !txnDataFile || uploadSuccess} // Блокируем кнопку, если файлы не выбраны или идет загрузка или успешная отправка данных
                    >
                        {uploadSuccess ? 'Данные успешно отправлены' : 'Отправить данные на сервер'}
                    </Button>
                </div>
                {/* Показываем текст "Загрузка..." во время загрузки */}
                {loading && (
                    <div className={cl.loadingText}>Загрузка...</div>
                )}
            </div>
        </form>
    );
};

export default FileUpload;
