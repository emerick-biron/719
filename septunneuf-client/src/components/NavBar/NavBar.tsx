import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { useEffect } from "react";
import CustomDawer from "../Common/CustomDawer";

const NavBar = () => {   

    const hero = useRecoilValue(heroState);
    useEffect(() => {
        console.log(hero);
    }
    , [hero]);
    
    return (
        <nav style={{backgroundColor: hero?.color}} className="bg-gray-200 p-4 fixed w-full top-0 ">
            <div className="text-black container mx-auto flex justify-center items-center">
                <div className="ml-auto pl-4">
                    <Link to="/shop-buy" className="mx-4 font-semibold hover:text-teal-200">Boutique</Link>
                    <Link to="/bag" className="mx-4 font-semibold hover:text-teal-200">Sac</Link>
                    <Link to="/inventory" className="mx-4 font-semibold hover:text-teal-200">Inventaire</Link>
                </div>
                <div className="ml-auto">
                    <CustomDawer />
                </div>
            </div>
        </nav>        
    );
}

export default NavBar;