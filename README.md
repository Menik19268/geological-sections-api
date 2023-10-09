# geological-sections-api -Get the latest code from develop/1.0.0 branch

# Creating a Section
curl --location --request POST 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/sections' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"section 5"
}'

# Getting a Section
curl --location --request GET 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/sections/1' \
--header 'Cookie: JSESSIONID=CD5953DD3DAFB6A925CBCAE33F8E8972'

# Update a Section
curl --location --request PUT 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/sections/5' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Section M"
}'

# Delete a Section
curl --location --request DELETE 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/sections/5'

# Create a new GeologicalClass
curl --location --request POST 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/geological-class' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "YourGeologicalClassName",
    "code": "YourGeologicalClassCode",
    "section": {
        "id": 1
    }
}'

# Get a GeologicalClass by ID
curl --location --request GET 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/geological-class/1'

# Update a GeologicalClass by ID
curl --location --request PUT 'http://localhost:8080/geological-sectionsapi/WWW12121212121/CG_USER/CG/geological-class/11' \
--header 'Content-Type: application/json' \
--data-raw '{

        "code": "GCOO"
    
}'

# Delete a GeologicalClass by ID
curl --location --request DELETE 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/geological-class/11'

# Get Sections by GeologicalClass Code
curl --location --request GET 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/section/by-code?code=GCNM'

# Import Data from XLS File
curl --location --request POST 'http://localhost:8080/geological-sections/api/import-export/WWW12121212121/CG_USER/CG/import?f' \
--form 'file=@"/Users/meniktennkoon/Public/detail.xls"'

# Get Import Status by ID
curl --location --request GET 'http://localhost:8080/geological-sections/api/import-export/WWW12121212121/CG_USER/CG/import/1'

# Launch Exporting
curl --location --request POST 'http://localhost:8080/geological-sectionsapi/import-export/WWW12121212121/CG_USER/CG/export'

# Get Export Status by ID
curl --location --request GET 'http://localhost:8080/geological-sections/api/import-export/WWW12121212121/CG_USER/CG/export/2'

# Download Exported File by Job ID
curl --location --request GET 'http://localhost:8080/geological-sections/api/import-export/WWW12121212121/CG_USER/CG/export/2/file'

# Health
curl --location --request GET 'http://localhost:8080/actuator/health'

# h2-console
curl --location --request GET 'http://localhost:8080/geological-sections-api/h2-console'


# Getting a Section with Basic Auth
curl --location --request GET 'http://localhost:8080/geological-sections/api/WWW12121212121/CG_USER/CG/sections/1' \
--header 'Cookie: JSESSIONID=CD5953DD3DAFB6A925CBCAE33F8E8972'

# Implementation of Basci Auth is in feature/basic-auth-impl branch 

