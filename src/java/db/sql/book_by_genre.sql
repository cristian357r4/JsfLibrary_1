SELECT b.id, b.name, b.isbn, b.page_count, b.publish_year, 
p.name AS publisher, a.fio AS author, g.name AS genre
FROM 
`book` AS b INNER JOIN `author` AS a ON b.author_id=a.id
INNER JOIN `genre` AS g on b.genre_id=g.id
INNER JOIN `publisher` AS p ON b.publisher_id=p.id
WHERE
b.genre_id=4
ORDER BY b.name
LIMIT 0,5;