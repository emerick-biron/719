import { Link } from "react-router-dom";

const NavBar = () => {   

    return (
            <nav className="bg-gray-300 p-4 fixed w-full top-0">
                <div className="text-black container mx-auto flex justify-center items-center">
                    <Link to="/shop-buy" className="mx-4 font-semibold">Boutique</Link>
                    <Link to="/bag" className="mx-4 font-semibold">Sac</Link>
                    <Link to="/inventory" className="mx-4 font-semibold">Inventaire</Link>
                </div>
            </nav>        
    );
}

export default NavBar;