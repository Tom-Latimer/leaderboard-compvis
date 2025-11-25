import React from "react";
import {FormProvider, type SubmitHandler, useFieldArray, useForm} from "react-hook-form";
import {z} from "zod";
import {zodResolver} from "@hookform/resolvers/zod";
import type {ApiError} from "../../types/errors/ApiError.ts";
import axios from "axios";
import {postChallengeSubmission} from "../../api/challenges/submissionApi.ts";
import FileDropzone from "../form/FileDropzone/FileDropzone.tsx";
import SuccessAlert from "../form/SuccessAlert/SuccessAlert.tsx";
import TrashIcon from "../../assets/trash.svg?react";
import "./challenge-submit-form.css";

const nameRegex = /^[\p{L} ,.'-]{2,}$/u;

const teamMemberSchema = z.object({
    firstName: z.string()
        .min(2, "Please enter team member's first name")
        .regex(nameRegex, "First name contains illegal characters")
        .max(20)
        .trim(),
    lastName: z.string()
        .min(2, "Please enter team member's last name")
        .regex(nameRegex, "Last name contains illegal characters")
        .max(50)
        .trim(),
    email: z.email("Please enter a valid email address")
        .max(50)
        .or(z.literal(""))
        .optional()
});

const schema = z.object({
    organization: z.string()
        .min(1, "Organization name is required")
        .max(50)
        .trim(),
    teamName: z.string()
        .min(1, "Team name is required")
        .max(50)
        .trim(),
    teamMembers: z.array(teamMemberSchema)
        .min(1, "At least one team member must be specified")
        .max(8, "Max team members exceeded; you can have up to 8 team members"),
    mainContactIndex: z.string()
        .min(1, "You must select one team member as the main contact"),
    file: z.array(z.instanceof(File))
        .refine(files => files.length == 1, "Please upload a file for submission")
        .refine(files => files[0]?.size <= 250 * 1024 * 1024, "File size must be under 250MB")
        .refine(files => ["text/csv"].includes(files[0]?.type), "Only .csv files are allowed")
})
    .superRefine((data, ctx) => {
        const index = Number(data.mainContactIndex);

        //check that the contact member index is a valid number
        if (isNaN(index)) {
            ctx.addIssue({
                code: "custom",
                message: "Invalid contact selection",
                path: ["mainContactIndex"]
            });
            return;
        }

        //check that the index is in range
        if (index < 0 || index >= data.teamMembers.length) {
            ctx.addIssue({
                code: "custom",
                message: "The selected main contact is invalid",
                path: ["mainContactIndex"]
            });
            return;
        }

        const mainContact = data.teamMembers[index];

        //check that main contact has email
        if (!mainContact.email || mainContact.email.trim() === "") {
            ctx.addIssue({
                code: "custom",
                message: "Email is required for the main contact",
                //put error on email field of main contact
                path: ["teamMembers", index, "email"],
            });
        }
    });

type FormInputs = z.infer<typeof schema>;

const ChallengeSubmitForm = ({challengeId}: { challengeId: string }) => {
    const methods = useForm<FormInputs>({
        resolver: zodResolver(schema),
        defaultValues: {
            file: [],
            teamMembers: [
                {firstName: "", lastName: "", email: ""}
            ],
            mainContactIndex: "0"
        }
    });

    const {
        register,
        handleSubmit,
        setError,
        control,
        formState: {errors, isSubmitting, isSubmitSuccessful},
        getValues,
        setValue
    } = methods;

    const {fields, append, remove} = useFieldArray({
        control,
        name: "teamMembers"
    })

    const onSubmit: SubmitHandler<FormInputs> = async (data) => {
        try {

            const contactIndex = Number(data.mainContactIndex);

            const transformedTeamMembers = data.teamMembers.map((member, index) => {
                return {
                    ...member,
                    isContact: index === contactIndex
                }
            });
            //construct data to be sent to api
            const formData = new FormData();

            const metadata = {
                organization: data.organization,
                teamName: data.teamName,
                teamMembers: transformedTeamMembers
            };

            //add uploaded file
            formData.append("metadata", new Blob([JSON.stringify(metadata)], {type: "application/json"}));

            formData.append("file", data.file[0]);

            await postChallengeSubmission(challengeId, formData);
        } catch (error: unknown) {
            if (axios.isAxiosError(error) && error.response?.data) {
                //convert the error to the standard sent by the backend
                const apiError = error.response.data as ApiError;

                //display errors returned from the backend
                switch (apiError.errorCode) {
                    case "ERR_FILE_INVALID":
                        setError("file", {
                            message: "Uploaded file format is invalid",
                        });
                        return;
                    case "ERR_TEAM_NAME_AE":
                        setError("teamName", {
                            message: "Team name already taken. Please enter a unique name for this challenge",
                        });
                        return;
                    case "ERR_CONTACT_EMAIL_AE":
                        const contactIndex = Number(data.mainContactIndex);

                        setError(`teamMembers.${contactIndex}.email`, {
                            message: "The primary contact email must be unique per team submission. Please use an email address that is not associated with another team's entry",
                        });
                        return;
                }
            }

            //generic error
            setError("root.apiError", {message: "An error has occurred while submitting the form"});
        }
    }

    const handleRemove = (index: number) => {
        const mainContactIndex = getValues("mainContactIndex");

        remove(index);

        setTimeout(() => {
            if (mainContactIndex === String(index)) {
                setValue("mainContactIndex", "0", {shouldValidate: true});
            } else if (Number(mainContactIndex) > index) {
                const newIndex = Number(mainContactIndex) - 1;
                setValue("mainContactIndex", String(newIndex), {shouldValidate: true});
            }
        }, 0);

    }

    return (
        <>
            {!isSubmitSuccessful ? (
                <FormProvider {...methods}>
                    <div className="form-container">
                        <form onSubmit={handleSubmit(onSubmit)} className="challenge-submit-form">
                            {errors.root?.apiError && (
                                <div className="error-message">
                                    {errors.root.apiError.message}
                                </div>
                            )}
                            <div className="form-control">
                                <label>Organization</label>
                                <input {...register("organization")} placeholder=""/>
                                {errors.organization && (
                                    <div className="error-message">{errors.organization.message}</div>)}
                            </div>
                            <div className="form-control">
                                <label>Team Name</label>
                                <input {...register("teamName")} placeholder=""/>
                                {errors.teamName && (<div className="error-message">{errors.teamName.message}</div>)}
                            </div>
                            <div className="team-member-container">
                                <div className="team-member-header">
                                    <label>Team Members</label>
                                </div>
                                <div className="table-wrapper">
                                    <table className="team-member-table">
                                        <thead>
                                        <tr>
                                            <th>First Name</th>
                                            <th>Last Name</th>
                                            <th>Email</th>
                                            <th className="text-center">Main Contact</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {fields.map((field, index) => (
                                            <React.Fragment key={field.id}>
                                                <tr>
                                                    <td>
                                                        <input {...register(`teamMembers.${index}.firstName`)}
                                                               placeholder="Jane"/>

                                                    </td>
                                                    <td>
                                                        <input {...register(`teamMembers.${index}.lastName`)}
                                                               placeholder="Doe"/>

                                                    </td>
                                                    <td>
                                                        <input {...register(`teamMembers.${index}.email`)}
                                                               placeholder="jane.doe@example.com"/>

                                                    </td>
                                                    <td>
                                                        <input
                                                            type="radio"
                                                            id={`mainContact-${field.id}`}
                                                            {...register("mainContactIndex")}
                                                            value={index}
                                                        />
                                                    </td>
                                                    <td>
                                                        {index > 0 ? (
                                                            <button type="button" className="trash-button"
                                                                    onClick={() => handleRemove(index)}>
                                                                <TrashIcon/>
                                                            </button>
                                                        ) : <button disabled type="button" className="trash-button">
                                                            <TrashIcon/>
                                                        </button>}
                                                    </td>
                                                </tr>
                                                {(errors.teamMembers?.[index]?.firstName ||
                                                    errors.teamMembers?.[index]?.lastName ||
                                                    errors.teamMembers?.[index]?.email) && (
                                                    <tr className="error-row">
                                                        <td colSpan={1}>
                                                            {errors.teamMembers?.[index]?.firstName && (
                                                                <div
                                                                    className="error-message">{errors.teamMembers[index].firstName.message}</div>
                                                            )}
                                                        </td>
                                                        <td colSpan={1}>
                                                            {errors.teamMembers?.[index]?.lastName && (
                                                                <div
                                                                    className="error-message">{errors.teamMembers[index].lastName.message}</div>
                                                            )}
                                                        </td>
                                                        <td colSpan={1}>
                                                            {errors.teamMembers?.[index]?.email && (
                                                                <div
                                                                    className="error-message">{errors.teamMembers[index].email.message}</div>
                                                            )}
                                                        </td>
                                                        <td colSpan={1}></td>
                                                        <td colSpan={1}></td>
                                                    </tr>
                                                )}
                                            </React.Fragment>

                                        ))}
                                        </tbody>
                                    </table>
                                    <button
                                        type="button"
                                        className="form-btn add-more-btn"
                                        onClick={() => append({firstName: "", lastName: "", email: ""})}>
                                        Add team member
                                    </button>

                                    {errors.mainContactIndex && (
                                        <div className="error-message">{errors.mainContactIndex.message}</div>
                                    )}
                                </div>
                            </div>
                            <div className="form-control">
                                <FileDropzone<FormInputs> name="file" accept=".csv" maxEntries={1}/>
                                {errors.file && (<div className="error-message">{errors.file.message}</div>)}
                            </div>
                            <div>
                                <button className="form-btn" disabled={isSubmitting} type="submit"
                                        value="Submit">Submit
                                </button>
                            </div>
                        </form>
                    </div>
                </FormProvider>
            ) : (
                <SuccessAlert
                    heading="Your submission was successfully uploaded!"
                    message="Thank you for your submission. Please wait patiently while our team reviews it. Once approved, it will appear on the leaderboard."
                />
            )}
        </>
    );
}

export default ChallengeSubmitForm;