import type {SubmissionListItem} from "../../../../types/submissions/submissionListItem.ts";
import {useEffect} from "react";
import {useNavigate} from "@tanstack/react-router";
import type {SortOrder} from "../../../../types/pagination/paginatedListProps.ts";
import UpArrowIcon from "../../../../assets/up-arrow.svg?react";
import DownArrowIcon from "../../../../assets/down-arrow.svg?react";
import EditIcon from "../../../../assets/edit.svg?react";
import "./submission-list.css";

type SortKey = "attachmentId" | null;

const SubmissionListRow = ({submission}: { submission: SubmissionListItem }) => {

    const navigate = useNavigate();

    return (
        <tr>
            <td>{submission.submissionId}</td>
            <td>{submission.challengeName}</td>
            <td>{submission.status}</td>
            <td>{submission.submittedAt}</td>
            <td>
                <button
                    className="list-button"
                    onClick={() =>
                        navigate({
                            to: "/admin/submissions/{$submissionId}_modal",
                            params: {submissionId: submission.submissionId} as { submissionId: string },
                            mask: {
                                to: "/admin/submissions/$submissionId",
                                unmaskOnReload: true,
                                params: {
                                    submissionId: submission.submissionId,
                                }
                            }
                        })
                    }
                >
                    <EditIcon/>
                </button>
            </td>
        </tr>
    );
}

const SubmissionListHeader = ({displayValue, value, sortOrder, sortKey, className, onClick}:
                              {
                                  displayValue: string,
                                  value: SortKey,
                                  sortOrder: SortOrder | null,
                                  sortKey: string | null,
                                  className: string,
                                  onClick: (key: SortKey) => void,
                              }) => {

    return (
        <th className={className} onClick={() => onClick(value)}>
            {displayValue}
            <span>
                {sortKey && sortKey === value ? (sortOrder === "asc" ? <UpArrowIcon className="active-icon"/> :
                        <DownArrowIcon className="active-icon"/>) :
                    <DownArrowIcon/>}
            </span>
        </th>
    );
}

const SubmissionList = ({items, sortKey, sortOrder, setSortKey, setSortOrder}:
                        {
                            items: SubmissionListItem[],
                            sortKey: string | null,
                            sortOrder: SortOrder | null,
                            setSortKey: (key: string | null) => void,
                            setSortOrder: (order: SortOrder | null) => void
                        }) => {
    //set initial sort
    useEffect(() => {
        setSortKey("attachmentId");
    }, []);

    const handleSort = (key: SortKey) => {
        if (sortKey === key) {
            setSortOrder(sortOrder === "asc" ? "desc" : "asc");
        } else {
            setSortKey(key as string);
            setSortOrder("desc");
        }
    }

    return (
        <div className="sub-list-container">
            <div className="sub-list-wrapper">
                <table className="sub-list">
                    <thead>
                    <tr>
                        <SubmissionListHeader
                            displayValue="ID"
                            value="attachmentId"
                            sortOrder={sortOrder}
                            sortKey={sortKey}
                            className="sortable-header"
                            onClick={handleSort}/>
                        <th>Challenge</th>
                        <th>Status</th>
                        <th>Date Submitted</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {items && items.length !== 0 ? (

                        items.map((sub) => (
                            <SubmissionListRow
                                key={sub.submissionId}
                                submission={sub}
                            />
                        ))) : (
                        <tr>
                            <td colSpan={5}></td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default SubmissionList;