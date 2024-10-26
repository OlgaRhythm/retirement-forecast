import axios from 'axios';

// Адрес сервера для API-запросов
const API_URL = 'http://localhost:8081'; // Адрес сервера для локальной разработки

/**
 * Загружает два файла на сервер и получает ответ с данными.
 * 
 * Функция принимает два файла — персональные данные и данные транзакций — и отправляет их 
 * на сервер в виде multipart/form-data. В ответе возвращаются предсказанные данные о досрочном выходе на пенсию
 * и связанные с ней затраты.
 *
 * @async
 * @function apiUploadFiles
 * @param {File} personalDataFile - Файл с персональными данными.
 * @param {File} txnDataFile - Файл с данными транзакций.
 * @returns {Promise<Object>} - Возвращает объект с предсказанными данными о досрочном выходе на пенсию и расчетными затратами.
 * 
 * @property {Object} predictedRetirementData - Прогнозируемые данные о пенсии.
 * @property {number} earlyRetirementCosts - Затраты на досрочный выход на пенсию.
 * @property {number} overallRetirementCosts - Общие затраты на пенсию.
 * 
 * @throws {Error} - Выбрасывает ошибку, если загрузка файлов не удалась.
 * 
 * @example
 * try {
 *   const result = await apiUploadFiles(personalDataFile, txnDataFile);
 *   console.log(result.predictedRetirementData);
 * } catch (error) {
 *   console.error('Ошибка:', error);
 * }
 */
export const apiUploadFiles = async (personalDataFile, txnDataFile) => {
  const formData = new FormData();
  formData.append('personalData', personalDataFile);
  formData.append('txnData', txnDataFile);

  try {
    const response = await axios.post(`${API_URL}/upload`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    // Парсинг JSON-ответа
    const { predictedRetirementData, earlyRetirementCosts, overallRetirementCosts } = response.data;

    console.log(predictedRetirementData);
    console.log(earlyRetirementCosts);
    console.log(overallRetirementCosts);

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