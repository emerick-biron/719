import BagIncubatorsView from "./BagIncubatorsView";
import BagTeam from "./BagTeam";

const BagView = () => {  
    return (
        <div className="mt-20 mx-20">
            <BagIncubatorsView />
            <BagTeam />
        </div>
    );
};

export default BagView;