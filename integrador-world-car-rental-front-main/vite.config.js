import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
   plugins: [react()],
   server: {
    port: 3000, // Cambia el puerto según tus necesidades
   },
   build: {
    outDir: 'build', // Puedes personalizar el nombre de la carpeta de construcción aquí
   },
   });



