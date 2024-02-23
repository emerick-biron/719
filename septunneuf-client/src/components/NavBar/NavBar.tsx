import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { heroState } from "../../recoil/HeroContext";
import { useEffect, useState } from "react";
import CustomDawer from "../Common/CustomDawer";

const NavBar = () => {   
    const hero = useRecoilValue(heroState);
    const [money, setMoney] = useState<number>(0);
    useEffect(() => {
        if (hero !== null && hero.money !== undefined) {
            setMoney(hero.money);
        }
    }, [hero]);
    
    return (
        <nav style={{backgroundColor: hero?.color}} className="bg-gray-200 p-4 fixed w-full top-0 ">
            <div className="text-black container flex justify-center items-center">
                <div className="ml-auto pl-4">
                    <Link to="/shop-buy" className="mx-4 font-semibold hover:text-teal-200">Boutique</Link>
                    <Link to="/bag" className="mx-4 font-semibold hover:text-teal-200">Inventaire</Link>
                    <Link to="/stock" className="mx-4 font-semibold hover:text-teal-200">Stockage</Link>
                </div>
                <div className="ml-auto flex">
                    <div className="mr-4 font-semibold">
                        {money} €
                    </div>
                    <CustomDawer />
                </div>
            </div>
        </nav>        
    );
}

export default NavBar;