# http://www.ebi.ac.uk/ontology-lookup/
# Dumps: ftp://ftp.ebi.ac.uk//pub/databases/ols/sqldump/
# Version: ols-2015-10-18-2200
# Result: mapping.csv
#

SELECT identifier,
GROUP_CONCAT(annotation_str_value SEPARATOR ' ') AS Origin,
CAST(GROUP_CONCAT(annotation_num_value SEPARATOR ' ') AS DECIMAL(10,2)) AS DiffAvg,
definition
FROM ols.term as T
LEFT JOIN ols.annotation AS A
ON T.term_pk = A.term_pk
WHERE
ontology_id='2081001'
AND (annotation_name = 'Origin' OR annotation_name = 'DiffAvg')
AND NOT definition LIKE 'OBSOLETE%'
GROUP BY identifier
HAVING
count(*) = 2
AND Origin != 'none'
AND DiffAvg IS NOT NULL
AND LENGTH(Origin) = 1
AND DiffAvg != 0
ORDER BY Origin, DiffAvg;
