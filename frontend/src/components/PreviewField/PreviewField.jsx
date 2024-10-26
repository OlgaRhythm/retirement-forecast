import React, { useState } from 'react';
import { Button } from 'antd';
import styles from './PreviewField.module.css';

/**
 * Компонент PreviewField для отображения изображений графиков.
 * Позволяет пользователю выбирать различные графики для отображения.
 * 
 * @returns {JSX.Element} Компонент для выбора и отображения графиков
 */
const PreviewField = () => {
  const [view, setView] = useState('empty'); // Отслеживает текущее представление
  const [selectedImage, setSelectedImage] = useState(null); // Сохраняет путь к выбранному изображению

  // Список доступных графиков с именами и путями к изображениям
  const graphicsData = [
    { name: 'Возраст', imagePath: 'images/graphics/age_graphic.png' },
    { name: 'Количество зубов', imagePath: 'images/graphics/teeth_graphic.png' },
    { name: 'Профессия', imagePath: 'images/graphics/job_graphic.png' },
  ];

  /**
   * Функция для рендеринга контента на основе текущего представления.
   * @returns {JSX.Element} Контент для отображения (изображение или пустое сообщение)
   */
  const renderContent = () => (
    view === 'image' && selectedImage ? (
      <img
        src={selectedImage} // Путь к выбранному изображению
        alt="График"
        style={{ width: '100%', height: 'auto' }}
      />
    ) : (
      <div>Пусто</div>
    )
  );

  /**
   * Обработчик нажатия на кнопку графика.
   * Устанавливает выбранное изображение и изменяет представление на 'image'.
   * 
   * @param {string} imagePath Путь к изображению графика
   */
  const handleGraphicClick = (imagePath) => {
    setSelectedImage(imagePath);
    setView('image');
  };

  return (
    <div className={styles.container}>
      {/* Контейнер для отображения графика */}
      <div className={styles.contentBox}>
        {renderContent()}
      </div>

      {/* Контейнер для кнопок выбора графиков */}
      <div className={styles.buttonContainer}>
        {graphicsData.map((graphic, index) => (
          <Button 
            key={index}
            type="default"
            onClick={() => handleGraphicClick(graphic.imagePath)} // Вызов обработчика выбора графика
            className={styles.button}
          >
            {graphic.name}
          </Button>
        ))}
      </div>
    </div>
  );
};

export default PreviewField;
