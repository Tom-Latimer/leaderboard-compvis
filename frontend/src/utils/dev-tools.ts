import {zodResolver} from "@hookform/resolvers/zod";

export const debugResolver = (schema: any) => {
    // zodResolver has type: (schema, context?, options?) => ...
    const resolver = zodResolver(schema); // this returns a RHF resolver function
    return async (values: any, context?: any, options?: any) => {
        console.log("Zod validating:", values);
        return resolver(values, context, options);
    };
};