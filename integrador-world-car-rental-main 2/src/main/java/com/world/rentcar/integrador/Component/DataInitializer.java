
package com.world.rentcar.integrador.Component;

import com.world.rentcar.integrador.enums.UserRol;
import com.world.rentcar.integrador.model.Direccion;
import com.world.rentcar.integrador.model.Sucursal;
import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.service.SucursalService;
import com.world.rentcar.integrador.service.UsuarioService;
import com.world.rentcar.integrador.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private UsuarioService usuarioService;
    @Override


    public void run(ApplicationArguments args) throws Exception {
//        //Creamos las Sucursales.
//        Sucursal sucursal1=new Sucursal(1L,"MisiconesCar",new HashSet<>(),new Direccion("Argentina", "123 Main St", 666, "Obera", "Misiones"));
//        Sucursal sucursal2=new Sucursal(2L,"MontevideoCar",new HashSet<>(),new Direccion("Uruguay", "123 Main St", 457, "La Paz", "Montevideo"));
//        Sucursal sucursal3=new Sucursal(3L,"BuenosAiresCar",new HashSet<>(),new Direccion("Argentina", "123 Main St", 458, "Sierras Bayas", "BuenosAires"));
//
//        //Creamos los Vehiculos.
//        Vehiculo vehiculo1=new Vehiculo(1L,"Sedan","Toyota",25000.0,true,"Automóvil",5,"Automóvil familiar con amplio espacio.","Motor de 2.0 litros","2000 cc","Automática","ABC123",5,Arrays.asList("imagen1.jpg", "imagen2.jpg", "imagen3.jpg"),sucursal1,new HashSet<>(),Arrays.asList(),new ArrayList<>());
//        Vehiculo vehiculo2 = new Vehiculo(2L, "Hatchback", "Honda", 22000.0, true, "Automóvil", 4, "Compacto y ágil para la ciudad.", "Motor de 1.6 litros", "1600 cc", "Manual", "XYZ456",4, Arrays.asList("imagen4.jpg", "imagen5.jpg", "imagen6.jpg"), sucursal1, new HashSet<>(),Arrays.asList(),new ArrayList<>());
//        Vehiculo vehiculo3 = new Vehiculo(3L, "SUV", "Ford", 30000.0, true, "Automóvil", 5, "Espacioso y versátil para aventuras familiares.", "Motor de 2.5 litros", "2500 cc", "Automática", "DEF789",4, Arrays.asList("imagen7.jpg", "imagen8.jpg", "imagen9.jpg"), sucursal1, new HashSet<>(),Arrays.asList(),new ArrayList<>());
//        Vehiculo vehiculo4 = new Vehiculo(4L, "Crossover", "Nissan", 28000.0, true, "Automóvil", 5, "Combina lo mejor de SUV y sedán.", "Motor de 2.2 litros", "2200 cc", "Automática", "MNO321",5, Arrays.asList("imagen16.jpg", "imagen17.jpg", "imagen18.jpg"), sucursal1, new HashSet<>(),Arrays.asList(),new ArrayList<>());
//
//    //Creamos los Usuarios.
//        Usuario usuarioAdmin = new Usuario(1L,"Facundo","Scholze","43833793","blablabla","facundoscholze","facu@gmail.com","$2a$10$Kcpt95345qI0Oe3gXl3BX.74s0CI.3VFztkkqIUeAb640m2x/sb8W", UserRol.ADMIN,new Direccion("Argentina", "Artigas 86", 666, "Obera", "Misiones"),new HashSet<>(),new ArrayList<>());
//        usuarioService.guardar(usuarioAdmin);
//
//        //Guardo los Usuarios en la base de datos.
//
//        //Guardo las Sucursales en la base de datos.
//        sucursalService.guardar(sucursal1);
//        sucursalService.guardar(sucursal2);
//        sucursalService.guardar(sucursal3);
//
//        ////Guardo los Vehiculos en la base de datos.
//        vehiculoService.guardar(vehiculo1);
//        vehiculoService.guardar(vehiculo2);
//        vehiculoService.guardar(vehiculo3);
//        vehiculoService.guardar(vehiculo4);


    }


}


