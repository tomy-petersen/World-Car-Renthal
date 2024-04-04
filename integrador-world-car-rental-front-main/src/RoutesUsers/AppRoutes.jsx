import {
    BrowserRouter as Router,
    Route,
    Link,
    Routes,    
  } from "react-router-dom";
import Categories from "./Categories";
import ContacUs from "./ContactUs";
import Home from "./Home";
import LogIn from "./LogIn"
import MyReservation from "./MyReservation";
import Payment from "./Payment"
import RegistrationForm from "./RegistrationForm"
import Rent from "./Rent"
import AddVehicle from "../RoutesAdmin/AddVehicle";
import EditVehicle from "../RoutesAdmin/EditVehicle"
import Header from "../Utils/Header";
import Footer from "../Utils/Footer"
import Galleries from "./Galleries";
import CarDetails from "./CarDetail";
import CarCards from "../Utils/CarCards";
import CarImg from "./CarImg";
import HomeAdmin from "../RoutesAdmin/HomeAdmin"
import ListVehicle from "../RoutesAdmin/ListVehicle"
import ListUser from "../RoutesAdmin/ListUser";
import Exit from "./Exit";
import EditUser from "../RoutesAdmin/EditUser";
import SearchResults from "./SearchResults";
import Politics from "./Politics";
import Favorites from "./Favorites";

  function AppRoutes(){
    return (
        <Router>
          <section className="app-container">       
            <Header/>                 
          <Routes>
            <Route path='/' element={<Home/>}/>
            <Route path='/login' element={<LogIn/>}/>
            <Route path='/categories' element={<Categories/>}/>
            <Route path='/contact' element={<ContacUs/>}/>                       
            <Route path='/myreservation' element={<MyReservation/>}/>
            <Route path='/payment' element={<Payment/>}/>
            <Route path='/registration' element={<RegistrationForm/>}/>
            <Route path='/rent' element={<Rent/>}/>
            <Route path='/edit/:id' element={<EditVehicle/>}/>
            <Route path="/edit" element={<EditVehicle/>}/>
            <Route path='/add' element={<AddVehicle/>}/>
            <Route path='/galeries' element={<Galleries/>}/>
            <Route path="/card" element={<CarCards />} />
            <Route path="/cardetails/:id" element={<CarDetails />} />
            <Route path="/carimg/:id" element={<CarImg/>} />
            <Route path="/homeAdmin" element={<HomeAdmin/>}/>
            <Route path="/list" element={<ListVehicle/>}/>
            <Route path="/listUser" element={<ListUser/>} />
            <Route path="/exit" element={<Exit/>}/>
            <Route path="/editUser/:id" element={<EditUser/>}/>
            <Route path="/result/:diaIncio/:diaEntrega/:pais/:ciudad" element={<SearchResults/>}/>
            <Route path="politics" element={<Politics/>}/>
            <Route path="/favorites" element={<Favorites/>}/>
          </Routes>                
          </section>  
          <Footer/>                    
        </Router>
    )
    
  }

  export default AppRoutes;