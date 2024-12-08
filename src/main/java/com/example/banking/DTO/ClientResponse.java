package com.example.banking.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientResponse {
    private int totalFilteredRecords;
    private List<ClientDTO> pageItems;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClientDTO {
        private Long id;
        private String accountNo;
        private Status status;
        private SubStatus subStatus;
        private boolean active;
        private List<Integer> activationDate;
        private String firstname;
        private String middlename;
        private String lastname;
        private String displayName;
        private String mobileNo;
        private Gender gender;
        private ClientType clientType;
        private ClientClassification clientClassification;
        private boolean isStaff;
        private Long officeId;
        private String officeName;
        private Timeline timeline;
        private LegalForm legalForm;
        private ClientNonPersonDetails clientNonPersonDetails;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Status {
            private int id;
            private String code;
            private String value;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SubStatus {
            private boolean active;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Gender {
            private boolean active;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ClientType {
            private boolean active;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ClientClassification {
            private boolean active;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Timeline {
            private List<Integer> submittedOnDate;
            private String submittedByUsername;
            private String submittedByFirstname;
            private String submittedByLastname;
            private List<Integer> activatedOnDate;
            private String activatedByUsername;
            private String activatedByFirstname;
            private String activatedByLastname;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class LegalForm {
            private Long id;
            private String code;
            private String value;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ClientNonPersonDetails {
            private Constitution constitution;
            private MainBusinessLine mainBusinessLine;

            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Constitution {
                private boolean active;
            }

            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class MainBusinessLine {
                private boolean active;
            }
        }
    }
}
