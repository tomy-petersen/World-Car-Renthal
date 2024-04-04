const Exit=()=>{

    const handleClick=()=>{
        confirm('¿Está seguro de que desea salir?');
        localStorage.clear()
        window.location.replace('/')
    }

    return(
        <button onClick={()=>{handleClick()}}>Cerrar Sesion</button>
    )
}

export default Exit;