import React from 'react';
import styled, { keyframes } from 'styled-components';

// Define la animación de carga usando keyframes
const spinAnimation = keyframes`
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
`;

// Estilo para el componente Loader
const LoaderWrapper = styled.div`
  display: inline-block;
  width: 30px;
  height: 30px;
  border: 4px solid rgba(195, 195, 195, 0.6);
  border-radius: 50%;
  border-top: 4px solid #3498db;
  animation: ${spinAnimation} 1s linear infinite;
`;

// Componente funcional Loader
const Loader = () => {
  return <LoaderWrapper />;
};

export default Loader;