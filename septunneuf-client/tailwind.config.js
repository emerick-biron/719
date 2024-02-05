/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      backgroundColor: {
        'red': '#F56565',
        'green': '#48BB78',
      }},
  },
  plugins: [],
}

