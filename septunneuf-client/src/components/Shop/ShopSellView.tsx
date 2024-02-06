import EggsShop from "../Egg.tsx/EggsShop"
import ShopNavBar from "./ShopNavBar"

const ShopSellView = () => {
    return (
        <div className="mt-20 mx-20">
            <ShopNavBar />
            <EggsShop 
                CustomButtonLabel="Vendre"
                color="red"
                sellOrBuy={true}
            />
        </div>
    )
}

export default ShopSellView