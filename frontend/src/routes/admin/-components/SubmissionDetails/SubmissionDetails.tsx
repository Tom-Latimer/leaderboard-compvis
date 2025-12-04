import type {AdminSubmissionDetails} from "../../../../types/submissions/adminSubmissionDetails.ts";
import SubmissionStatusForm from "../SubmissionStatusForm/SubmissionStatusForm.tsx";
import {formatBytes} from "../../../../utils/formatBytes.ts";
import AuthenticatedLink from "../../../../components/authenticatedLink/AuthenticatedLink.tsx";

const SubmissionDetails = ({submissionDetails, onSuccess}: {
    submissionDetails: AdminSubmissionDetails,
    onSuccess: (data: any) => void
}) => {

    const downloadUrl = `/submissions/${submissionDetails.submissionId}/download`;

    return (
        <>
            <div className="section">
                <h3 className="section-header">Overview</h3>
                <div className="section-body">
                    <div className="overview-section-item">
                        <p className="label">Challenge Name</p>
                        <p className="value">{submissionDetails.challengeName}</p>
                    </div>
                    <div className="overview-section-item">
                        <p className="label">Organization</p>
                        <p className="value">{submissionDetails.organization}</p>
                    </div>
                    <div className="overview-section-item">
                        <p className="label">Team Name</p>
                        <p className="value">{submissionDetails.teamName}</p>
                    </div>
                </div>
            </div>
            <div className="section">
                <h3 className="section-header">Team Members</h3>
                <div className="section-body">
                    <div className="tm-details-table-wrapper">
                        <table className="tm-details-table">
                            <thead>
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th className="text-center">Primary Contact</th>
                            </tr>
                            </thead>
                            <tbody>
                            {submissionDetails.teamMembers && submissionDetails.teamMembers.length > 0 && (
                                submissionDetails.teamMembers.map((member, index) => (
                                    <tr key={index}>
                                        <td>{member.firstName}</td>
                                        <td>{member.lastName}</td>
                                        <td>{member.email}</td>
                                        <td className="text-center">
                                            <input
                                                type="radio"
                                                checked={member.isContact}
                                                disabled
                                            />
                                        </td>
                                    </tr>
                                ))
                            )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div className="section">
                <h3 className="section-header">Uploaded File</h3>
                <div className="section-body">
                    <AuthenticatedLink url={downloadUrl} fileName={submissionDetails.fileName}>
                        {submissionDetails.fileName} ({formatBytes(submissionDetails.fileSize)})
                    </AuthenticatedLink>
                </div>
            </div>
            <div className="section">
                <h3 className="section-header">Status</h3>
                <div className="section-body">
                    <SubmissionStatusForm
                        submissionDetails={submissionDetails}
                        onSuccess={onSuccess}
                    />
                </div>
            </div>
        </>
    );
}

export default SubmissionDetails;