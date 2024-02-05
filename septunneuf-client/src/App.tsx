import { BrowserRouter, Route, Router, Routes } from "react-router-dom";
import InventoryView from "./components/Inventory/InventoryView";
import NavBar from "./components/NavBar/NavBar";
import ShopSellView from "./components/Shop/ShopSellView";
import ShopBuyView from "./components/Shop/ShopBuyView";

function App() {
	return (
		<BrowserRouter>
			<header>
				<NavBar />
			</header>
			<Routes>
				<Route index element={<InventoryView />} />
				<Route path="/inventory" element={<InventoryView />} />
				<Route path="/shop-sell" element={<ShopSellView />} />
				<Route path="/shop-buy" element={<ShopBuyView />} />
			</Routes>
		</BrowserRouter>

	);
}

export default App;
