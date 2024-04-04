import React from 'react'

const ContainerCategories = () => {
  return (    
<div className="container">
  <h2>CATEGOTIAS</h2>
   <div className="square-container">
    <div className="square">
      <img src="../src/Img/4x4.gif" alt="Imagen 1" />
      <h2>4x4</h2>
    </div>
   <div className="square">
      <img src="../src/Img/suvs.gif" alt="Imagen 2" />
      <h2>Suvs</h2>
    </div>
    <div className="square">
      <img src="../src/Img/sedan.gif" alt="Imagen 3" />
      <h2>Sedan</h2>
    </div>
    <div className="square">
      <img src="../src/Img/electrico.gif" alt="Imagen 4" />
      <h2>Electrico</h2>
    </div>
  </div>
</div>
  )
}

export default ContainerCategories