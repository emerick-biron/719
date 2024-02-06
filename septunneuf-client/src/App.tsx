import { BrowserRouter, Route, Routes, useNavigate } from "react-router-dom";
import InventoryView from "./components/Inventory/InventoryView";
import NavBar from "./components/NavBar/NavBar";
import ShopSellView from "./components/Shop/ShopSellView";
import ShopBuyView from "./components/Shop/ShopBuyView";
import { heroState } from "./recoil/HeroContext";
import HeroLogin from "./components/Hero/HeroLogin";
import { useEffect } from "react";
import { RecoilRoot, useRecoilState } from "recoil";
import HeroCreate from "./components/Hero/HeroCreate";

function App() {
	const [hero, setHero] = useRecoilState(heroState);

	useEffect(() => {
	  const storedHero = localStorage.getItem('hero');
  
	  if (storedHero) {
		const parsedHero = JSON.parse(storedHero);
		setHero(parsedHero);
	  }
	}, [setHero]);
	
	if (!hero) {
	  return (
		<BrowserRouter>
		  <Routes>
			<Route index element={<HeroLogin />} />
			<Route path="/login-hero" element={<HeroLogin />} />
			<Route index path="/create-hero" element={<HeroCreate />} />
		  </Routes>
		</BrowserRouter>
	  );
	}
	
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
			<footer className="h-96"></footer>
		</BrowserRouter>

	);
}

const RootApp = () => (
	<RecoilRoot>
	  <App />
	</RecoilRoot>
  );

export default RootApp;

