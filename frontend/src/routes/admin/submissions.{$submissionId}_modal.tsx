import {createFileRoute, useNavigate} from '@tanstack/react-router'
import {useEffect, useRef} from "react";
import "./admin-modal.css";
import XIcon from "../../assets/x.svg?react";
import {getSubmissionDetails} from "../../api/adminSubmissionApi.ts";
import SubmissionDetails from "./-components/SubmissionDetails/SubmissionDetails.tsx";

export const Route = createFileRoute('/admin/submissions/{$submissionId}_modal')({
    component: RouteComponent,
    loader: async ({params}) => {
        const submissionId = params.submissionId;
        return await getSubmissionDetails(submissionId);
    }
})

function RouteComponent() {

    const navigate = useNavigate();
    const dialogRef = useRef<HTMLDialogElement>(null);

    //get the submission details
    const submissionDetails = Route.useLoaderData();

    //run only on mount and dismount
    useEffect(() => {

        const dialog = dialogRef.current;

        if (!dialog) return;

        dialog.showModal();

        dialog.addEventListener("close", closeModal);

        //clean up event listeners
        return () => {
            dialog.removeEventListener("close", closeModal);
        }

    }, []);

    function closeModal() {
        dialogRef.current?.close();
        navigate({
            to: "/admin/submissions"
        });
    }

    function handleSubmissionSuccess() {
        closeModal();
    }

    return (
        <dialog className="admin-modal-container" ref={dialogRef}
                onClick={(e) => {
                    if (e.currentTarget === e.target) {
                        closeModal();
                    }
                }}>
            <div className="admin-modal">
                <div className="admin-modal-header">
                    <div className="header-content">
                        <h2>Submission Details</h2>
                        <button className="x-button" onClick={closeModal}>
                            <XIcon/>
                        </button>
                    </div>
                </div>
                <div className="admin-modal-body">
                    <SubmissionDetails
                        submissionDetails={submissionDetails}
                        onSuccess={handleSubmissionSuccess}
                    />
                </div>
                <div className="admin-modal-footer">
                    <div className="footer-content">
                        <button className="cancel=btn" onClick={closeModal}>Cancel</button>
                        <button className="confirm-btn" form="submission-status-form" type="submit">Confirm</button>
                    </div>
                </div>
            </div>
        </dialog>
    );
}
