import {FormProvider, type SubmitHandler, useForm} from "react-hook-form";
import {z} from "zod";
import {zodResolver} from "@hookform/resolvers/zod";
import type {ApiError} from "../../types/errors/ApiError.ts";
import axios from "axios";
import {postChallengeSubmission} from "../../api/challenges/submissionApi.ts";
import FileDropzone from "../form/FileDropzone/FileDropzone.tsx";
import "./challenge-submit-form.css";
import SuccessAlert from "../form/SuccessAlert/SuccessAlert.tsx";

const nameRegex = /^[\p{L} ,.'-]{2,}$/u;

const schema = z.object({
    firstName: z.string()
        .min(2, "Please enter your first name")
        .regex(nameRegex, "First name contains illegal characters")
        .max(20)
        .trim(),
    lastName: z.string()
        .min(2, "Please enter your last name")
        .regex(nameRegex, "Last name contains illegal characters")
        .max(50)
        .trim(),
    email: z.email("Please enter a valid email address")
        .max(50),
    file: z.array(z.instanceof(File))
        .refine(files => files.length == 1, "Please upload a file for submission")
        .refine(files => files[0]?.size <= 250 * 1024 * 1024, "File size must be under 250MB")
        .refine(files => ["text/csv"].includes(files[0]?.type), "Only .csv files are allowed")
});

type FormInputs = z.infer<typeof schema>;
const debugResolver = (schema: any) => {
    // zodResolver has type: (schema, context?, options?) => ...
    const resolver = zodResolver(schema); // this returns a RHF resolver function
    return async (values: any, context?: any, options?: any) => {
        console.log("Zod validating:", values);
        return resolver(values, context, options);
    };
};

const ChallengeSubmitForm = ({challengeId}: { challengeId: string }) => {
    const methods = useForm<FormInputs>({
        resolver: debugResolver(schema),
        defaultValues: {
            file: [],
        }
    });

    const {
        register,
        handleSubmit,
        setError,
        formState: {errors, isSubmitting, isSubmitSuccessful},
    } = methods;

    const onSubmit: SubmitHandler<FormInputs> = async (data) => {
        try {
            const formData = new FormData();

            const metadata = {
                firstName: data.firstName,
                lastName: data.lastName,
                email: data.email,
            };
            formData.append("metadata", new Blob([JSON.stringify(metadata)], {type: "application/json"}));

            formData.append("file", data.file[0]);

            await postChallengeSubmission(challengeId, formData);
        } catch (error: unknown) {
            if (axios.isAxiosError(error) && error.response?.data) {
                //convert the error to the standard sent by the backend
                const apiError = error.response.data as ApiError;

                if (apiError.error === "FileValidationException") {
                    setError("file", {
                        message: apiError.message,
                    });
                }
            }
        }
    }

    return (
        <>
            {!isSubmitSuccessful ? (
                <FormProvider {...methods}>
                    <form onSubmit={handleSubmit(onSubmit)} className="challenge-submit-form">
                        <div className="form-control">
                            <div className="horizontal-form-control">
                                <div className="form-control w-50">
                                    <label>First Name</label>
                                    <input {...register("firstName")} />
                                </div>
                                <div className="form-control w-50">
                                    <label>Last Name</label>
                                    <input {...register("lastName")} />
                                </div>
                            </div>
                            {errors.firstName && (<div className="error-message">{errors.firstName.message}</div>)}
                            {errors.lastName && (<div className="error-message">{errors.lastName.message}</div>)}
                        </div>
                        <div className="form-control">
                            <label>Email</label>
                            <input type="email" {...register("email")} placeholder="Email Address"/>
                            {errors.email && (<div className="error-message">{errors.email.message}</div>)}
                        </div>
                        <div className="form-control">
                            <FileDropzone<FormInputs> name="file" accept=".csv" maxEntries={1}/>
                            {errors.file && (<div className="error-message">{errors.file.message}</div>)}
                        </div>
                        <div>
                            <button className="form-button" disabled={isSubmitting} type="submit"
                                    value="Submit">Submit
                            </button>
                        </div>
                    </form>
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