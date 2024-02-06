
const Monster = (props:{data: any}) => {

    
    return (
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: props.data.color}} className="m-2 mt-4 mb-0 w-32 h-32" /> 
            <div className="font-bold">#{props.data.id}</div>
        </div>
    );
}

export default Monster;