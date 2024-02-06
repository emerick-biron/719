import Incubator from "../Incubator/Incubator"
import ShopNavBar from "./ShopNavBar"
import EggsShop from "../Egg.tsx/EggsShop"

const ShopBuyView = () => {
    return (
        <div className="mt-20 mx-20">
            <ShopNavBar />
            <Incubator />
            <br />
            <EggsShop 
                CustomButtonLabel="Acheter"
                color="red"
            />
        </div>
    )
}

export default ShopBuyView