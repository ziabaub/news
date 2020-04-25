INSERT INTO public.author(
	 id,name, surname)
	VALUES
	( -793570791,'ziad', 'sarrih'),
	( 320793357,'Teresa', 'Silva'),
	( -848396103,'Christie', 'Jacobs');

	INSERT INTO public.news(
	id,author_id,title, shorttext, fulltext, creationdate, modificationdate)
	VALUES
	(1,-793570791,'Hungry Designers', 'Apple’s ‘Hungry Designers Wanted’ is an amazing idea.',
			'Apple’s ‘Hungry Designers Wanted’ is an amazing idea. Making the the famous apple logo half eaten look like a person to represent the hungry job applicants is extremely playful. It presents a challenge to designers; Apple needs motivated individuals to work for them and they are encouraging them to send their CVs to the email provided.',
			'1999-01-08', '1999-01-28'),
    (2,320793357,'Imaginary Person?', 'Alkemy decided to create a job post for ...',
			'Alkemy decided to create a job post for a person that doesn’t exist. Their ad message says “if you think you are our imaginary person, send us your real CV’ which translates to if you believe you have the skills to work for us, go ahead and apply for the job. The image used for this advert has movie references, and this makes it stronger. Overall, it’s both clever and catchy just like a job ad should be.',
			'1998-02-08', '1998-02-28'),
	(3,-848396103,'Life’s Too Short', 'Apple’s ‘This ad has a powerful  ...',
            'This ad has a powerful message from the Germany job-hunting website jobsintown.de that says you have to make sure to choose your job wisely to avoid ending up in the wrong one. Using a series of well-designed ads that depict people manually working ATMs, jukeboxes, coffee machines, instant photo booths and washing machines, it shows how miserable a bad job can make you. It’s a very imaginative way to encourage jobseekers to apply for the ‘right’ job.',
			'2000-05-08', '2000-05-28');

INSERT INTO public.tag(
	id,name)
	VALUES
	(2051606012,'1980 Olympics'),
	(101188556,'1943 Lincoln Penny'),
	(1417284252,'1-800-GOT-JUNK');

INSERT INTO public.roles(
    id,role)
VALUES
(-1623386144, 'admin'),
(1584530828, 'journalist'),
(-1025379343, 'archive');



INSERT INTO public.users(
	 roles_id,name, surname, login, password)
	VALUES
	(-1623386144,'Ale', 'Balloushi', 'Bisho', 'sdfgsdgsdf'),
	(1584530828,'Nina', 'Pope', 'Nina', 'fgsdfg'),
	(-1025379343,'Angelica', 'Craig', 'Angelica', 'sdfsdfsd');



INSERT INTO public.news_tag(
	news_id, tag_id)
	VALUES
	(1, 2051606012),
	(2, 1417284252),
	(3, 101188556);

