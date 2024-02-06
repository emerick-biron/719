import { Link } from "react-router-dom";

const ShopNavBar = () => {
    return (
        <div className="flex justify-center space-x-0">
            <Link to="/shop-buy" className="bg-green-300 px-16 py-2 font-semibold hover:bg-green-400">Acheter</Link>
            <Link to="/shop-sell" className="bg-red-300 px-16 py-2  font-semibold hover:bg-red-400">Vendre</Link>
        </div>
    )
}

export default ShopNavBar;
