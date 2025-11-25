package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.validation;

import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.SubmissionTeamMemberDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ContactMustHaveEmailValidator implements ConstraintValidator<ContactMustHaveEmail,
        SubmissionTeamMemberDto> {

    @Override
    public boolean isValid(SubmissionTeamMemberDto submissionTeamMemberDto,
                           ConstraintValidatorContext context) {

        //if a member is not the contact, email is not required
        if (submissionTeamMemberDto.getIsContact() == null || !submissionTeamMemberDto.getIsContact()) {
            return true;
        }

        //if a member is a contact, they need a non-blank email
        if (StringUtils.isNotBlank(submissionTeamMemberDto.getEmail())) {
            return true;
        }

        //set custom field name for validation message
        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(
                        context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("email")
                .addConstraintViolation();

        return false;
    }
}
