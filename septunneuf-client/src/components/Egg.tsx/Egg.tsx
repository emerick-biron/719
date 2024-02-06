const Egg = (props:{data: any}) => {

    return (
        <div className="flex flex-col items-center justify-center">
            <div style={{ background: props.data.color}} className="m-2 mt-4 mb-0 w-32 h-32 rounded-full flex items-center justify-center" >
                {`${props.data.price}$`}
            </div>
        </div>
    );
}

export default Egg;