import type {OrderedItem} from "../pagination/orderedItem.ts";

export interface SubmissionListItem extends OrderedItem {
    submissionId: string;
    challengeName: string;
    status: string;
    dateSubmitted: string;
}