import axios from 'axios';

const API_URL = 'http://176.124.201.26:8080'; // Адрес сервера
//const API_URL = 'http://localhost:8081'; // Адрес сервера

// Функция для загрузки двух файлов на сервер
export const apiUploadFiles = async (personalDataFile, txnDataFile) => {
  const formData = new FormData();
  formData.append('personalData', personalDataFile); // Добавляем первый файл
  formData.append('txnData', txnDataFile); // Добавляем второй файл

  try {
   const response = await axios.post(`${API_URL}/upload`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    // Парсинг JSON-ответа
    const { predictedRetirementData, earlyRetirementCosts, overallRetirementCosts } = response.data;

    console.log(predictedRetirementData); // Выведет массив объектов
    console.log(earlyRetirementCosts); // Выведет число 150000.00
    console.log(overallRetirementCosts); // Выведет число 300000.00


    // Возвращаем структурированные данные
    return {
      predictedRetirementData,
      earlyRetirementCosts,
      overallRetirementCosts,
    };
  } catch (error) {
    console.error('Ошибка при загрузке файлов:', error);
    throw error;
  }
};