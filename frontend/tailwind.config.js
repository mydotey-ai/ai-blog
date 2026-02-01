export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'klein-blue': '#0022FF',
        'klein-blue-hover': '#001ACC',
      },
      fontFamily: {
        'display': ['Monument Extended', 'Helvetica Now Display', 'Arial Black', 'sans-serif'],
        'sans': ['Space Grotesk', 'Inter', 'system-ui', 'sans-serif'],
        'mono': ['JetBrains Mono', 'SF Mono', 'monospace'],
      },
    },
  },
  plugins: [],
}
