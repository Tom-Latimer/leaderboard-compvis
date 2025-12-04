import {z} from "zod";
import {Controller, type SubmitHandler, useForm, useWatch} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {patchSubmissionStatus} from "../../../../api/adminSubmissionApi.ts";
import type {SubmissionStatusUpdateBody} from "../../../../types/submissions/submissionStatusUpdateBody.ts";
import type {SubmissionStatus} from "../../../../types/submissions/submissionFilter.ts";
import type {AdminSubmissionDetails} from "../../../../types/submissions/adminSubmissionDetails.ts";
import "./submission-status-form.css";

const schema = z.object({
    status: z.enum(["APPROVED", "REJECTED", "PENDING"]),
    maxPrecision: z.number()
        .min(0, "Max precision must be greater than or equal to 0")
        .max(1, "Max precision must be less than or equal to 1")
        .nullable(),
    maxRecall: z.number()
        .min(0, "Max recall must be greater than or equal to 0")
        .max(1, "Max recall must be less than or equal to 1")
        .nullable(),
    split: z.number()
        .min(0, "Split must be greater than or equal to 0")
        .max(1, "Split must be less than or equal to 1")
        .nullable(),
}).superRefine((data, ctx) => {

    //validate the 'APPROVED' fields if selected
    if (data.status === "APPROVED") {
        if (data.maxPrecision === null || data.maxPrecision === undefined) {
            ctx.addIssue({
                code: "custom",
                message: "Max precision is required",
                path: ["maxPrecision"]
            });
        }

        if (data.maxRecall === null || data.maxRecall === undefined) {
            ctx.addIssue({
                code: "custom",
                message: "Max recall is required",
                path: ["maxRecall"]
            });
        }

        if (data.split === null || data.split === undefined) {
            ctx.addIssue({
                code: "custom",
                message: "Split is required",
                path: ["split"]
            });
        }
    }

    //no validation for reject option as it has no additional fields
});

type FormInputs = z.infer<typeof schema>;

const SubmissionStatusForm = ({submissionDetails, onSuccess}: {
    submissionDetails: AdminSubmissionDetails,
    onSuccess: (data: any) => void
}) => {

    const {
        control,
        register,
        handleSubmit,
        formState: {errors},
        setError
    } = useForm<FormInputs>({
        resolver: zodResolver(schema),
        defaultValues: {
            status: submissionDetails.status,
            maxRecall: submissionDetails.maxRecall,
            maxPrecision: submissionDetails.maxPrecision,
            split: submissionDetails.split
        }
    });

    const status = useWatch({control, name: "status"});

    const onSubmit: SubmitHandler<FormInputs> = async (data) => {
        try {

            const status = data.status.toUpperCase() as SubmissionStatus

            //no need to make api call if status did not change
            if (status === submissionDetails.status &&
                (data.maxRecall === submissionDetails.maxRecall &&
                    data.maxPrecision === submissionDetails.maxPrecision &&
                    data.split === submissionDetails.split)) {

                //call the onSuccess callback from the parent
                onSuccess(data);
                return;
            }

            const requestBody: SubmissionStatusUpdateBody = {
                status: status,
                maxPrecision: null,
                maxRecall: null,
                split: null
            }

            if (status === "APPROVED") {
                requestBody.maxPrecision = data.maxPrecision;
                requestBody.maxRecall = data.maxRecall;
                requestBody.split = data.split;
            }

            await patchSubmissionStatus(submissionDetails.submissionId, requestBody);

            //call the onSuccess callback from the parent
            onSuccess(data);
        } catch (error: unknown) {
            //generic error
            console.log(error);
            setError("root.apiError", {message: "An error has occurred while updating the status"});
        }
    }

    return (
        <form id="submission-status-form" onSubmit={handleSubmit(onSubmit)}>
            <div className="select-control">
                <Controller
                    control={control}
                    name="status"
                    render={({field}) => (
                        <select {...field}>
                            <option value="PENDING">Pending</option>
                            <option value="APPROVED">Approved</option>
                            <option value="REJECTED">Rejected</option>
                        </select>
                    )}
                />
                {errors.status && (<div className="error-message">{errors.status.message}</div>)}
            </div>

            {errors.root?.apiError && (
                <div className="error-message generic-error-message">
                    {errors.root.apiError.message}
                </div>
            )}

            {status === "APPROVED" && (
                <div>
                    <div className="form-control">
                        <div className="horizontal-control">
                            <label>Max Precision:</label>
                            <input {...register("maxPrecision", {valueAsNumber: true})} />
                        </div>
                        {errors.maxPrecision && (
                            <div className="error-message">{errors.maxPrecision.message}</div>)}
                    </div>
                    <div className="form-control">
                        <div className="horizontal-control">
                            <label>Max Recall:</label>
                            <input {...register("maxRecall", {valueAsNumber: true})} />
                        </div>
                        {errors.maxRecall && (
                            <div className="error-message">{errors.maxRecall.message}</div>)}
                    </div>
                    <div className="form-control">
                        <div className="horizontal-control">
                            <label>Split:</label>
                            <input {...register("split", {valueAsNumber: true})} />
                        </div>
                        {errors.split && (
                            <div className="error-message">{errors.split.message}</div>)}
                    </div>
                </div>
            )}
        </form>
    );
}

export default SubmissionStatusForm;