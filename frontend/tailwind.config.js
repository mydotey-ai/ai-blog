export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'klein-blue': {
          DEFAULT: '#0022FF',
          light: '#0066FF',
          dark: '#001ACC',
        },
      },
      fontFamily: {
        'display': ['Monument Extended', 'Helvetica Now Display', 'Arial Black', 'sans-serif'],
        'sans': ['Space Grotesk', 'Inter', 'system-ui', 'sans-serif'],
        'mono': ['JetBrains Mono', 'SF Mono', 'monospace'],
      },
      boxShadow: {
        'glow-sm': '0 0 20px rgba(0,34,255,0.3)',
        'glow-md': '0 0 40px rgba(0,34,255,0.15)',
        'glow-lg': '0 0 60px rgba(0,34,255,0.2)',
        'card': '0 2px 8px rgba(0,34,255,0.08)',
        'card-hover': '0 20px 60px rgba(0,34,255,0.25)',
        'elevation-1': '0 2px 8px rgba(0,34,255,0.08)',
        'elevation-2': '0 8px 24px rgba(0,34,255,0.12)',
        'elevation-3': '0 16px 48px rgba(0,34,255,0.16)',
      },
      animation: {
        'float': 'float 20s ease-in-out infinite',
        'float-delayed': 'float 25s ease-in-out 5s infinite',
        'fade-in': 'fadeIn 0.6s ease-out',
        'fade-in-up': 'fadeInUp 0.6s ease-out',
        'fade-in-down': 'fadeInDown 0.6s ease-out',
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translate(0, 0) rotate(0deg)' },
          '33%': { transform: 'translate(30px, -30px) rotate(120deg)' },
          '66%': { transform: 'translate(-20px, 20px) rotate(240deg)' },
        },
        fadeIn: {
          from: { opacity: '0' },
          to: { opacity: '1' },
        },
        fadeInUp: {
          from: { opacity: '0', transform: 'translateY(30px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
        fadeInDown: {
          from: { opacity: '0', transform: 'translateY(-30px)' },
          to: { opacity: '1', transform: 'translateY(0)' },
        },
      },
      backdropBlur: {
        xs: '2px',
      },
    },
  },
  plugins: [],
}
