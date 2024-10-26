import React from 'react';
import cl from './HomePage.module.css';
import Header from '../../components/Header/Header';
import ResultView from '../../components/ResultView/ResultView';

/**
 * Компонент HomePage, отображающий главную страницу с хедером и результатами загрузки файлов.
 *
 * @returns {JSX.Element} Главная страница приложения
 */
const HomePage = () => {
    return (
        <div className={cl.container}>
            <Header /> {/* Компонент заголовка */}
            <ResultView /> {/* Компонент для загрузки файлов и отображения результатов */}
            {/*<PreviewField />*/} {/* Компонент для вывода информации о модели */}
        </div>
    );
};

export default HomePage;