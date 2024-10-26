import React, { useState } from 'react';
import { Button, Table, Typography } from 'antd';
import styles from './PreviewField.module.css'; // Импортируем стили

const { Title } = Typography;

const PreviewField = () => {
  const [view, setView] = useState('empty'); // Состояние для отслеживания текущего представления
  const [selectedImage, setSelectedImage] = useState(null); // Состояние для выбранного графика

  // Данные для таблицы (будут браться первые 5 записей из .csv)
  const tableData = [
    { key: '1', name: 'John Doe', age: 32, address: '10 Downing St.' },
    { key: '2', name: 'Jane Doe', age: 28, address: '20 Downing St.' },
  ];

  // Список кнопок для графиков
  const graphicsData = [
    { name: 'Возраст', imagePath: 'images/graphics/age_graphic.png' },
    { name: 'Количество зубов', imagePath: 'images/graphics/teeth_graphic.png' },
    { name: 'Профессия', imagePath: 'images/graphics/job_graphic.png' },
  ];

  const renderContent = () => {
    switch (view) {
      case 'table':
        return (
          <Table
            dataSource={tableData}
            columns={[
              { title: 'Name', dataIndex: 'name', key: 'name' },
              { title: 'Age', dataIndex: 'age', key: 'age' },
              { title: 'Address', dataIndex: 'address', key: 'address' },
            ]}
            pagination={false}
            style={{ width: '100%' }} // Заставляет таблицу занимать всю ширину контейнера
          />
        );
      case 'image':
        return (
          <img
            src={selectedImage} // Используем состояние для отображения выбранного изображения
            alt="График"
            style={{ width: '100%', height: 'auto' }}
          />
        );
      case 'empty':
      default:
        return <div>Пусто</div>;
    }
  };

  const handleGraphicClick = (imagePath) => {
    setSelectedImage(imagePath); // Устанавливаем выбранное изображение
    setView('image'); // Устанавливаем представление на 'image'
  };

  return (
    <div className={styles.container}>
      <div className={styles.contentBox}>
        {renderContent()}
      </div>

      <div className={styles.buttonContainer}>
        {graphicsData.map((graphic, index) => (
          <Button 
            key={index}
            type="default"
            onClick={() => handleGraphicClick(graphic.imagePath)} // Вызываем функцию для обработки нажатия
            className={styles.button} // Используем класс из CSS
          >
            {graphic.name}
          </Button>
        ))}
        {/* <Button onClick={() => setView('table')} className={styles.button}>
          Таблица
        </Button> */}
      </div>
    </div>
  );
};

export default PreviewField;
