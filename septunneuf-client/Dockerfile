# Étape de construction
FROM node:alpine as build

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

ARG REACT_APP_API_URL

ENV REACT_APP_API_URL $REACT_APP_API_URL

COPY . .

RUN npm run build

# Étape de production
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
