# Используйте официальный образ Node.js как базовый
FROM node:18 AS build

# Установите рабочую директорию
WORKDIR /app

# Скопируйте package.json и package-lock.json
COPY package*.json ./

# Установите зависимости
RUN npm install

# Скопируйте все файлы в рабочую директорию
COPY . .

# Постройте приложение
RUN npm run build

# Используйте легкий образ для сервера
FROM nginx:alpine

# Скопируйте собранное приложение в директорию, откуда Nginx будет его обслуживать
COPY --from=build /app/build /usr/share/nginx/html

# Экспонируйте порт, который будет использовать Nginx
EXPOSE 80

# Запустите Nginx
CMD ["nginx", "-g", "daemon off;"]