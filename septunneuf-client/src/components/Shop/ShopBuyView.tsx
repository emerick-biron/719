import Incubator from "../Incubator/Incubator"
import ShopNavBar from "./ShopNavBar"
import EggsShopBuy from "../Egg.tsx/EggsShopBuy"

const ShopBuyView = () => {
    return (
        <div className="mt-20 mx-20">
            <ShopNavBar />
            <Incubator />
            <EggsShopBuy />
        </div>
    )
}

export default ShopBuyView