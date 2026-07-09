ALTER TABLE users ADD COLUMN IF NOT EXISTS email VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS full_name VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS company_name VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(255);

UPDATE users SET email = username || '@legacy.local' WHERE email IS NULL;
UPDATE users SET full_name = username WHERE full_name IS NULL;
UPDATE users SET role = 'JOBSEEKER' WHERE role IS NULL;

CREATE SEQUENCE IF NOT EXISTS job_post_post_id_seq;
SELECT setval(
    'job_post_post_id_seq',
    COALESCE((SELECT MAX(post_id) FROM job_post), 0) + 1,
    false
);
ALTER TABLE job_post ALTER COLUMN post_id SET DEFAULT nextval('job_post_post_id_seq');
ALTER SEQUENCE job_post_post_id_seq OWNED BY job_post.post_id;
