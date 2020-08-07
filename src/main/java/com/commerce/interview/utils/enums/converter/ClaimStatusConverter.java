package com.commerce.interview.utils.enums.converter;

import com.commerce.interview.business.claim.entity.ClaimStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ClaimStatusConverter implements AttributeConverter<ClaimStatus, String> {
    @Override
    public String convertToDatabaseColumn(ClaimStatus claimStatus) {
        if (claimStatus == null) {
            return null;
        }
        return claimStatus.getStatus();
    }

    @Override
    public ClaimStatus convertToEntityAttribute(String claimStatus) {
        if (claimStatus == null) {
            return null;
        }

        return Stream.of(ClaimStatus.values())
                .filter(c -> c.getStatus().equals(claimStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
