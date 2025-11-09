import CheckIcon from "../../../assets/circled-check.svg?react";
import "./success-alert.css";

const SuccessAlert = ({heading, message}: { heading: string, message: string }) => {
    return (
        <div className="success-alert-container">
            <div className="success-alert">
                <CheckIcon/>
                <div className="alert-body">
                    <h2>{heading}</h2>
                    <p>{message}</p>
                </div>
            </div>
        </div>

    );
}

export default SuccessAlert;