import { Link } from "react-router-dom";

const ShopNavBar = () => {
    return (
        <div className="flex justify-center space-x-4">
            <Link to="/shop-buy" className="bg-green px-4 py-2 font-semibold rounded-md">Acheter</Link>
            <Link to="/shop-sell" className="bg-red px-4 py-2  font-semibold rounded-md">Vendre</Link>
        </div>
    )
}

export default ShopNavBar;
