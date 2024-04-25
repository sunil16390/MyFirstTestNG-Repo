package resuable;

public class CreateRequestBody {

    public String createBankEmployeeRequestBody(String name, String year, String dob, String address, String salary){
        String body = "{\n" +
                "\t\"name\": \""+name+"\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"year\": \""+year+"\",\n" +
                "\t\t\"DOB\": \""+dob+"\",\n" +
                "\t\t\"Address\": \""+address+"\",\n" +
                "\t\t\"Salary\": \""+salary+"\"\n" +
                "\t}\n" +
                "}";
        return body;
    }
    public String CreateUserJsonBody(String name, String ssn){
    String body = "{\n" +
            "    \"name\": \""+name+"\",\n" +
            "    \"job\": \"API Test Lead\",\n" +
            "    \"address\": \"123 Dublin Blvd USA\",\n" +
            "    \"SNN\": \""+ssn+"\"\n" +
            "}";
    return body;
    }



    public String CreateCelsiusToFahrenheitXMLbody(String celsius){
    String request_body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
            "  <soap12:Body>\n" +
            "    <CelsiusToFahrenheit xmlns=\"https://www.w3schools.com/xml/\">\n" +
            "      <Celsius>"+celsius+"</Celsius>\n" +
            "    </CelsiusToFahrenheit>\n" +
            "  </soap12:Body>\n" +
            "</soap12:Envelope>";
    return request_body;
    }

    public String addNewProductJsonbody(String productName){
        String request_body = "{\n" +
                "    \"name\": \""+productName+"\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2019,\n" +
                "        \"price\": 1849.99,\n" +
                "        \"CPU model\": \"Intel Core i9\",\n" +
                "        \"Hard disk size\": \"1 TB\"\n" +
                "    }\n" +
                "}";
        return request_body;
    }

}
