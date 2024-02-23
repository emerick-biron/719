import EggsShopSell from "../Egg.tsx/EggsShopSell"
import MonsterShopSell from "../Monster/MonsterShopSell"
import ShopNavBar from "./ShopNavBar"

const ShopSellView = () => {
    return (
        <div className="mt-20 mx-20">
            <ShopNavBar />
            <EggsShopSell />
            <MonsterShopSell />
        </div>
    )
}

export default ShopSellView