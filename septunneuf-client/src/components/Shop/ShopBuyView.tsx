import Incubator from "../Common/Incubator"
import ShopNavBar from "./ShopNavBar"

const ShopBuyView = () => {
    return (
        <div className="mt-20">
             <ShopNavBar />
            <h1 className="font-bold text-2xl">Incubateurs</h1>
            <Incubator />
        </div>
    )
}

export default ShopBuyView